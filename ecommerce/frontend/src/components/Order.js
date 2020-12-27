import { Button, Collapse, notification, Popconfirm } from 'antd'
import './Order.less'
import { Purchase } from './Purchase'
import * as R from 'ramda'
import { orderStatusInvMap, sleep } from '../utils'
import * as moment from 'moment'
import { api } from '../api'
import { StopOutlined } from '@ant-design/icons'
import { useState } from 'react'

const HorizontalLabel = ({ label, children }) => {
    return (
        <div>
            <span className="order-header-label">{label}:&nbsp;</span>
            <br />
            <span className="order-header-text">{children}</span>
        </div>
    )
}

export const Order = ({ order, onOrderCancelled }) => {
    const [cancelLoading, setCancelLoading] = useState(false)

    const onCancelOrder = async event => {
        event.stopPropagation() // prevent click on collapse

        setCancelLoading(true)
        try {
            await api.post(`/checkout/cancelorder/${order.id}`)
            notification.success({ message: 'Order was cancelled' })
            onOrderCancelled()
        } catch (error) {
            notification.warning({ message: 'There was an error with your request.' })
            console.error(error)
        } finally {
            setCancelLoading(false)
        }
    }

    const onShowOrderDetails = async event => {
        event.stopPropagation()
    }

    const firstPurchase = order.order_all_purchase[0]

    const totalPrice =
        order.prices.total_price ??
        order.purchases.map(purchase => purchase.unit_price * purchase.amount).reduce(R.add, 0)

    const canCancel =
        R.any(purchase => orderStatusInvMap[purchase.status] !== 'cancelled', order.purchases) &&
        R.none(purchase => orderStatusInvMap[purchase.status] === 'at_cargo', order.purchases) &&
        R.none(purchase => orderStatusInvMap[purchase.status] === 'delivered', order.purchases)

    const orderDate = moment.utc(firstPurchase.purchase_date).format('DD/MM/YYYY HH:mm ')
    return (
        <Collapse defaultActiveKey="order" collapsible={false}>
            <Collapse.Panel
                key="order"
                collapsible={false}
                showArrow={false}
                header={
                    <div className="order-header">
                        <HorizontalLabel label="Order Date">{orderDate}</HorizontalLabel>
                        <HorizontalLabel label="Total Price">
                            {totalPrice} {firstPurchase.currency ?? 'TL'}
                        </HorizontalLabel>
                        <HorizontalLabel label="Receiver">
                            {[firstPurchase.address.name, firstPurchase.address.surname].filter(Boolean).join(' ')}
                        </HorizontalLabel>
                        <HorizontalLabel label="# of Products">{order.purchases.length}</HorizontalLabel>
                        <div className="order-header-details">
                            {canCancel && (
                                <Popconfirm
                                    title="Delete this card?"
                                    onConfirm={onCancelOrder}
                                    okText="Yes"
                                    cancelText="No">
                                    <Button
                                        danger
                                        icon={<StopOutlined />}
                                        onClick={e => e.stopPropagation()}
                                        loading={cancelLoading}>
                                        Cancel Order
                                    </Button>
                                </Popconfirm>
                            )}
                            &nbsp;
                            <Button onClick={onShowOrderDetails} type="dashed">
                                Show Order Details
                            </Button>
                        </div>
                    </div>
                }>
                <div className="order-purchases">
                    {order.purchases.map(purchase => {
                        return <Purchase key={purchase.id} purchase={purchase} />
                    })}
                </div>
            </Collapse.Panel>
        </Collapse>
    )
}
