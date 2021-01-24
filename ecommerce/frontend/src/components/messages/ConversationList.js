import './ConversationList.less'
import 'react-chat-elements/dist/main.css'

import { ChatList } from 'react-chat-elements'

import { getRetroAvatarUrl } from '../../utils'
import { Empty, Skeleton } from 'antd'

export const ConversationList = ({ className, conversations, onSelectConversation }) => {
    if (conversations === null) {
        return (
            <div className={className}>
                <Skeleton active title paragraph={{ rows: 1 }} avatar />
                <Skeleton active title paragraph={{ rows: 2 }} avatar />
                <Skeleton active title paragraph={{ rows: 1 }} avatar />
                <Skeleton active title paragraph={{ rows: 2 }} avatar />
            </div>
        )
    }

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
