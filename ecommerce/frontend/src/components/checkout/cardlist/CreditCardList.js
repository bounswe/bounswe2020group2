import { Button, List, Spin, notification } from 'antd'
import { sleep } from '../../../utils'
import { useEffect, useState } from 'react'
import { CreditCard } from './CreditCard'
import { CreditCardModal } from './CreditCardModal'
import { useAppContext } from '../../../context/AppContext'
import './CreditCardList.less'

export const CreditCardList = () => {
    const [cardList, setCardList] = useState([])
    const { user } = useAppContext();

    const getCardList = async userId => {
        let cards = []
        try {
            setIsLoading(true)
            await sleep(2000)
            cards = [
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
            ]
        } catch (error) {
            notification.warning({ message: 'There was an error with your request.' })
            console.error(error)
        } finally {
            setIsLoading(false)
            return Promise.resolve(cards)
        }
    }

    useEffect(() => {
        async function fetch() {
            const cards = await getCardList(user?.id)
            setCardList(cards)
            setSelectedCard(cards.length > 0 ? cards[0].id : null)
        }
        fetch();
    }, [user])

    const [selectedCard, setSelectedCard] = useState()
    const [addVisible, setAddVisible] = useState(false)
    const [isLoading, setIsLoading] = useState(false)

    const onCardSelect = cardId => setSelectedCard(cardId)

    const onCardInfoChange = () => {getCardList(user?.id).then(cards => setCardList(cards))}

    

    return (
        <div className="cardlist-container">
            <div className="cardlist-title">
                Select a credit card
                <Button onClick={() => setAddVisible(true)} type="dashed">
                    Add a new credit card
                </Button>
                <CreditCardModal 
                    mode='add' 
                    onCancel={() => setAddVisible(false)} 
                    onSuccess={() => {
                        setAddVisible(false);
                        onCardInfoChange();
                    }} 
                    visible={addVisible} />
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
