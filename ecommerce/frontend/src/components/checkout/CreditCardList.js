import { Button, Card, List, Radio } from 'antd';
import { useState } from 'react';
import { CreditCard } from './CreditCard';
import './CreditCardList.less'

export const CreditCardList = () => {
    const [cardList, setCardList] = useState([
        {
            cardProps: {
                cvc: "123",
                expiry: "1230",
                name: "Özdeniz Dolu",
                number: "5555555555554444"
                },
            cardId: "44313",
            cardName:"My Mastercard"
        },
        {
            cardProps: {
                cvc: "123",
                expiry: "1230",
                name: "Özdeniz Dolu",
                number: "4111111111111111"
                },
            cardId: "14312",
            cardName:"My Visacard"
        },
    ]);
    const [selectedCard, setSelectedCard] = useState();
    
    const onCardSelect = (value) => setSelectedCard(value)

    return <div className="cardlist-container">
        <div className="cardlist-title">
            Select a credit card
            <Button type="dashed">Add a new credit card</Button>
        </div>
        <div className="cardlist-list">
            
            <List
                grid={{ gutter: 0}}
                dataSource={cardList}
                renderItem={item => (
                <List.Item>
                    <CreditCard {...item} selected={selectedCard === item.cardId} onSelect={onCardSelect} />
                </List.Item>
            )}/>
        </div>
    </div> 
}