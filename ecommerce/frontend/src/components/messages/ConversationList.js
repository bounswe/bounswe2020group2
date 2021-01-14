import './ConversationList.less'
import 'react-chat-elements/dist/main.css'

import { ChatList } from 'react-chat-elements'

import { getRetroAvatarUrl } from '../../utils'

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
