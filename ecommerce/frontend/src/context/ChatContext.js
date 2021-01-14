import { notification } from 'antd'
import constate from 'constate'
import { useState } from 'react'

import { api } from '../api'
import { formatConversation } from '../utils'

function useChat() {
    const [conversations, setConversations] = useState(null)
    const [conversation, setConversation] = useState(null)
    const [isLoading, setIsLoading] = useState(true)

    const getConversations = async () => {
        setIsLoading(true)
        try {
            const { data } = await api.get(`/messages`)
            if (!data?.status?.successful) throw new Error(data)
            const conversations = data.conversations.map(formatConversation)

            setConversations(conversations)
            setConversation(conversations[0])
        } catch (error) {
            notification.warning({ message: 'There was an error while fetching messages' })
            console.error(error)
        } finally {
            setIsLoading(false)
        }
    }

    const sendMessage = async message => {
        console.log('sendMessage message', message)
        setIsLoading(true)
        try {
            const { data } = await api.post(`/messages`, {
                receiver_id: message.receiver_id,
                text: message?.text ?? null,
                attachment: message?.attachment ?? null,
            })
            if (!data?.status?.successful) throw new Error(data)
            await getConversations()
        } catch (error) {
            notification.error({ message: 'There was an error while sending message' })
            console.error(error)
        } finally {
            setIsLoading(false)
        }
    }

    return { conversation, setConversation, conversations, getConversations, sendMessage }
}

export const [ChatContextProvider, useChatContext] = constate(useChat)
