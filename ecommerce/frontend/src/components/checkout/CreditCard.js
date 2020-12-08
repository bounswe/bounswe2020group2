import Cards from 'react-credit-cards'
import 'react-credit-cards/es/styles-compiled.css'
import './CreditCard.less'
import cls from 'classnames'
import { DeleteOutlined, EditOutlined } from '@ant-design/icons'
import { useState } from 'react'
import { CreditCardModal } from './CreditCardModal'
import { Popconfirm, Spin, notification } from 'antd'
import { formatCreditCard, sleep } from '../../utils'

export const CreditCard = ({
    card,
    selected = false,
    onSelect = () => {},
    onCardInfoChange = () => {},
}) => {
    const [editVisible, setEditVisible] = useState(false)
    const [isLoading, setIsLoading] = useState(false)

    
    const onDelete = async () => {
        try {
            setIsLoading(true)
            await sleep(2000)
            onCardInfoChange();
            notification.success({ message: 'You have successfully deleted your credit card!' })
            
        } catch (error) {
            notification.warning({ message: 'There was an error with your request.' })
        } finally {
            setIsLoading(false)
        }
    }

    const onSuccessfulEdit = () => {
        setEditVisible(false);
        onCardInfoChange();
    }

    return (
        <div
            onClick={() => onSelect(card?.id)}
            className={cls('creditcard-container', {
                'creditcard-container__selected': selected,
            })}>
            <Spin spinning={isLoading}>
                <div className="creditcard-header">
                    <input
                        type="radio"
                        name="selectedCard"
                        checked={selected}
                        value={card.id}
                        onChange={val => onSelect(val.target.value)}
                    />
                    &nbsp;{card.name}
                    <div className="creditcard-header-icons">
                        <CreditCardModal
                            onCancel={() => setEditVisible(false)}
                            onSuccess={() => {
                                setEditVisible(false);
                                onCardInfoChange();
                            }}
                            mode='edit'
                            visible={editVisible}
                            card={card}
                        />
                        <EditOutlined
                            onClick={() => {
                                setEditVisible(true)
                            }}
                        />{' '}
                        &nbsp;
                        <Popconfirm
                            title="Delete this card?"
                            onConfirm={onDelete}
                            okText="Yes"
                            cancelText="No">
                            <DeleteOutlined />
                        </Popconfirm>
                    </div>
                </div>
                <div className="creditcard-card">
                    {card && <Cards className="creditcard-card" {...formatCreditCard(card)} />}
                </div>
                
            </Spin>
        </div>
    )
}
