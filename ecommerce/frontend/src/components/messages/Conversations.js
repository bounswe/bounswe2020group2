import './Conversations.less'
import 'react-chat-elements/dist/main.css'

import { useEffect } from 'react'

import { useChatContext } from '../../context/ChatContext'
import { Conversation } from './Conversation'
import { ConversationList } from './ConversationList'
import { Empty } from 'antd'

export const Conversations = () => {
    const { conversations, getConversations, conversation, setConversation } = useChatContext()

    const onSelectConversation = conversation => {
        setConversation(conversation)
    }

    useEffect(() => {
        async function fetch() {
            await getConversations()
        }
        fetch()
    }, [])

    if (conversations !== null && !conversations.length) {
        return (
            <div className="conversations-empty">
                <Empty description={'You have no conversations'} />
            </div>
        )
    }

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
