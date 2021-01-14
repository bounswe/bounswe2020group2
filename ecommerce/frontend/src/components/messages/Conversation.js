import './Conversation.less'
import 'react-chat-elements/dist/main.css'

import { UploadOutlined } from '@ant-design/icons'
import { Button, Input, Upload } from 'antd'
import { getBase64 } from 'image-blobber'
import { useEffect, useRef, useState } from 'react'
import { MessageList } from 'react-chat-elements'

import { useChatContext } from '../../context/ChatContext'

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
                className="conversation-message-list"
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
        </div>
    )
}
