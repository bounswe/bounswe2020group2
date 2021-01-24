import { Button, List, Spin, notification } from 'antd'
import { useEffect, useState } from 'react'
import { CreditCard } from './CreditCard'
import { CreditCardModal } from './CreditCardModal'
import { useAppContext } from '../../context/AppContext'
import './CreditCardList.less'
import { api } from '../../api'

export const CreditCardList = ({ onSelect = () => {} }) => {
    const [cardList, setCardList] = useState([])
    const { user } = useAppContext()

    const fetchCardList = async () => {
        setIsLoading(true)
        try {
            const {
                data: { status, cards },
            } = await api.get(`/customer/${user.id}/cards`)
            if (status.successful) {
                setCardList(cards)
                const defaultCard = cards[0]?.id
                setSelectedCard(defaultCard)
                onSelect(defaultCard)
            } else {
                notification.warning({ message: status.message })
            }
        } catch (error) {
            notification.warning({ message: 'There was an error with your request.' })
            console.error(error)
        } finally {
            setIsLoading(false)
        }
    }

    useEffect(() => {
        fetchCardList()
    }, [user])

    const [selectedCard, setSelectedCard] = useState()
    const [addVisible, setAddVisible] = useState(false)
    const [isLoading, setIsLoading] = useState(false)

    const onCardSelect = cardId => {
        setSelectedCard(cardId)
        onSelect(cardId)
    }

    const onCardInfoChange = () => {
        fetchCardList()
    }

    return (
        <div className="cardlist-container">
            <div className="cardlist-title">
                Select a credit card
                <Button onClick={() => setAddVisible(true)} type="dashed">
                    Add a new credit card
                </Button>
                <CreditCardModal
                    mode="add"
                    onCancel={() => setAddVisible(false)}
                    onSuccess={() => {
                        setAddVisible(false)
                        onCardInfoChange()
                    }}
                    visible={addVisible}
                />
            </div>
            <div className="cardlist-list">
                <Spin spinning={isLoading}>
                    <List
                        locale={{ emptyText: 'Add a new payment option!' }}
                        grid={{ gutter: 0 }}
                        dataSource={cardList}
                        renderItem={card => (
                            <List.Item>
                                <CreditCard
                                    card={card}
                                    onCardInfoChange={onCardInfoChange}
                                    selected={selectedCard === card.id}
                                    onSelect={onCardSelect}
                                />
                            </List.Item>
                        )}
                    />
                </Spin>
            </div>
        </div>
    )
}
