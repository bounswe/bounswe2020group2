import { List, notification, Avatar } from 'antd'
import { useEffect, useState } from 'react'
import { api } from '../../api'
import { formatConversation } from '../../utils'
import './Conversations.less'
import { MessageList, ChatList } from 'react-chat-elements'
import 'react-chat-elements/dist/main.css'

export const ConversationList = ({ className, conversations, onSelectConversation }) => {
    if (conversations === null) return null

    const formatConversation = conversation => {
        const lastMessage = conversation.messages[conversation.messages.length - 1]

        return {
            conversation,
            avatar: 'https://facebook.github.io/react/img/logo.svg',
            alt: conversation.counterpart.name,
            title: conversation.counterpart.name,
            subtitle: lastMessage.text,
            date: lastMessage.date,
            unread: 0,
        }

        // return {
        //     position: message.sent_by_me ? 'right' : 'left',
        //     type: 'text',
        //     text: message.text,
        //     date: message.date,
        // }
    }

    return (
        // <div className={className}>
        <ChatList
            className={className}
            dataSource={conversations.map(formatConversation)}
            onClick={chatItem => onSelectConversation(chatItem.conversation)}
        />
        // </div>
    )

    return (
        <div className={className}>
            <List
                dataSource={conversations}
                rowKey={'id'}
                renderItem={conversation => {
                    const lastMessage = conversation.messages[conversation.messages.length - 1]
                    return (
                        <List.Item>
                            <List.Item.Meta
                                avatar={
                                    <Avatar src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png" />
                                }
                                title={conversation.counterpart.name}
                                description={lastMessage.text}
                            />
                        </List.Item>
                    )
                }}></List>
        </div>
    )
}

export const Conversation = ({ className, conversation }) => {
    if (conversation === null) return null

    const formatMessage = message => {
        return {
            position: message.sent_by_me ? 'right' : 'left',
            type: 'text',
            text: message.text,
            date: message.date,
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
        </div>
    )
}

export const Conversations = () => {
    const [conversations, setConversations] = useState(null)
    const [isLoading, setIsLoading] = useState(true)

    const [conversation, setConversation] = useState(null)

    const fetch = async () => {
        setIsLoading(true)
        try {
            const { data: conversations } = await api.get(`/messages`)
            setConversations(conversations.map(formatConversation))
            setConversation(conversations.map(formatConversation)[0])
        } catch (error) {
            notification.warning({ message: 'There was an error while fetching messages' })
            console.error(error)
        } finally {
            setIsLoading(false)
        }
    }

    const onSelectConversation = conversation => {
        console.log(conversation)
        setConversation(conversation)
    }

    useEffect(() => {
        fetch()
    }, [])

    return (
        <div className="conversations">
            <ConversationList
                className={'conversations-list'}
                conversations={conversations}
                onSelectConversation={onSelectConversation}
            />
            <Conversation className={'conversation-messages'} conversation={conversation} />
        </div>
    )
}
