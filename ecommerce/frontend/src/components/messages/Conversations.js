import { List, notification, Avatar, Input, Button, Upload } from 'antd'
import { useEffect, useRef, useState } from 'react'
import { useChatContext } from '../../context/ChatContext'
import { api } from '../../api'
import { formatConversation, getRetroAvatarUrl } from '../../utils'
import './Conversations.less'
import { MessageList, ChatList } from 'react-chat-elements'
import { UploadOutlined } from '@ant-design/icons'
import 'react-chat-elements/dist/main.css'
import { getBase64 } from 'image-blobber'

export const ConversationList = ({ className, conversations, onSelectConversation }) => {
    if (conversations === null) return null

    const formatConversation = conversation => {
        const lastMessage = conversation.messages[conversation.messages.length - 1]

        return {
            conversation,
            avatar: getRetroAvatarUrl(conversation.counterpart.id),
            alt: conversation.counterpart.name,
            title: conversation.counterpart.name,
            subtitle: lastMessage.text,
            date: lastMessage.date,
            unread: 0,
        }
    }

    return (
        <ChatList
            className={className}
            dataSource={conversations.map(formatConversation)}
            onClick={chatItem => onSelectConversation(chatItem.conversation)}
        />
    )
}

export const Conversation = ({ className, conversation }) => {
    const [file, setFile] = useState(null)
    const [value, setValue] = useState('')
    const [loading, setLoading] = useState(false)
    const { sendMessage } = useChatContext()

    if (conversation === null) return null

    const formatMessage = message => {
        const type = message.attachment_url !== null ? 'photo' : 'text'
        return {
            position: message.sent_by_me ? 'right' : 'left',
            type,
            text: message.text,
            date: message.date,
            ...(type === 'photo' ? { data: { uri: message.attachment_url } } : {}),
        }
    }

    const onInputChange = event => {
        setValue(event.target.value)
    }

    const onSendMessage = async () => {
        let message = {
            receiver_id: conversation.counterpart.id,
            text: value.trim(),
        }

        if (file) {
            const image = await getBase64(file)
            message.attachment = image.base64
        }

        try {
            setLoading(true)
            console.log('message', message)
            await sendMessage(message)
            setValue('')
            setFile(null)
        } finally {
            setLoading(false)
        }
    }

    return (
        <div className={className}>
            <MessageList
                className="message-list"
                lockable={true}
                toBottomHeight={'100%'}
                dataSource={conversation.messages.map(formatMessage)}
            />
            <div style={{ display: 'flex' }}>
                <Upload
                    listType="picture"
                    maxCount={1}
                    fileList={[file].filter(Boolean)}
                    beforeUpload={file => {
                        setFile(file)
                        return false
                    }}>
                    <Button icon={<UploadOutlined />}>Upload</Button>
                </Upload>
                <Input value={value} onChange={onInputChange} onPressEnter={onSendMessage} />
                <Button type="primary" onClick={onSendMessage} loading={loading}>
                    Send
                </Button>
            </div>
            {/* <Input
                ref={inputRef}
                placeholder="Type here..."
                defaultValue={value}
                onChange={onInputChange}
                onPressEnter={console.log}
                rightButtons={<Button color="white" backgroundColor="black" text="Send" />}
            /> */}
        </div>
    )
}

export const Conversations = () => {
    const { conversations, getConversations, conversation, setConversation } = useChatContext()

    const onSelectConversation = conversation => {
        setConversation(conversation)
    }

    useEffect(() => {
        getConversations()
    }, [])

    return (
        <div className="conversations">
            <ConversationList
                className={'conversations-list'}
                conversations={conversations}
                onSelectConversation={onSelectConversation}
            />
            <Conversation className={'conversations-messages'} conversation={conversation} />
        </div>
    )
}
