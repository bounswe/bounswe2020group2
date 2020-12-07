import { Button, List, Spin, notification } from 'antd'
import { sleep } from '../../utils'
import { useEffect, useState } from 'react'
import { CreditCard } from './CreditCard'
import { AddCardModal } from './AddCardModal'
import './CreditCardList.less'

export const CreditCardList = () => {
    const [cardList, setCardList] = useState([])

    const getCardList = async userId => {
        try {
            setIsLoading(true)
            await sleep(2000)
            setIsLoading(false)
            setCardList([
                {   
                    id: 44313,
                    name: 'My Mastercard',
                    owner_name: 'Özdeniz Dolu',
                    serial_number: "5555555555554444",
                    expiration_date: {
                        month: 12,
                        year: 2030
                    },
                    cvc: 123
                },
                {
                    id: 12332,
                    name: 'My Visacard',
                    owner_name: 'Özdeniz Dolu',
                    serial_number: "4111111111111111",
                    expiration_date: {
                        month: 12,
                        year: 2030
                    },
                    cvc: 123
                },
            ])
        } catch (error) {
            notification.warning({ message: 'There was an error with your request.' })
        }
    }

    useEffect(() => getCardList(''), [])

    const [selectedCard, setSelectedCard] = useState()
    const [addVisible, setAddVisible] = useState(false)
    const [isLoading, setIsLoading] = useState(false)

    const onCardSelect = cardId => setSelectedCard(cardId)

    const onCardInfoChange = async () => getCardList('')

    

    return (
        <div className="cardlist-container">
            <div className="cardlist-title">
                Select a credit card
                <Button onClick={() => setAddVisible(true)} type="dashed">
                    Add a new credit card
                </Button>
                <AddCardModal onCancel={() => setAddVisible(false)} onSuccessfulAdd={onCardInfoChange} visible={addVisible} />
            </div>
            <div className="cardlist-list">
                <Spin spinning={isLoading}>
                    <List
                        locale={{emptyText: "Add a new payment option!"}}
                        grid={{ gutter: 0 }}
                        dataSource={cardList}
                        renderItem={card => (
                            <List.Item>
                                <CreditCard card={card} onCardInfoChange={onCardInfoChange} selected={selectedCard === card.id} onSelect={onCardSelect} />
                            </List.Item>
                        )}
                    />
                </Spin>
            </div>
        </div>
    )
}
