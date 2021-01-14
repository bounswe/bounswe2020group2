import { notification } from 'antd'
import constate from 'constate'
import { useState } from 'react'

import { api } from '../api'
import { formatConversation } from '../utils'

import useInterval from '@use-it/interval'

function useChat() {
    const [conversations, setConversations] = useState(null)
    const [counterpart, setCounterpart] = useState(null)
    const [isLoading, setIsLoading] = useState(true)

    const setConversation = conversation => {
        setCounterpart(conversation.counterpart.id)
    }

    useInterval(async () => {
        await getConversations()
    }, 5000)

    const getConversations = async () => {
        setIsLoading(true)
        try {
            const { data } = await api.get(`/messages`)
            if (!data?.status?.successful) throw new Error(data)
            const conversations = data.conversations.map(formatConversation)

            setConversations(conversations)
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

    let _counterpart = null
    let _conversation = null

    // this part is a bit complicated but if you follow it slowly you will understand if I store the
    // conversation object then on every poll a branch new object is created which makes the
    // conversation pane jump to the first conversation in the array even though it might be another
    // one holding a counterpart id instead doesn't change so the conversation is fetched based on
    // its counterpart id since a conversation is uniquely identified with its counterpart
    if (conversations === null) {
        _counterpart = null
        _conversation = null
    } else {
        if (counterpart === null) {
            _conversation = conversations[0]
            _counterpart = _conversation.counterpart.id
        } else {
            _counterpart = counterpart
            _conversation = conversations.find(conversation => conversation.counterpart.id == _counterpart)

            if (_conversation === null) {
                _conversation = conversations[0]
                _counterpart = _conversation.counterpart.id
            } else {
                // do nothing
            }
        }
    }

    return {
        conversation: _conversation,
        counterpart: _counterpart,
        setConversation,
        conversations,
        getConversations,
        sendMessage,
    }
}

export const [ChatContextProvider, useChatContext] = constate(useChat)
