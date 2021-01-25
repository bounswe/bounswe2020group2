import './Order.less'

import { StopOutlined } from '@ant-design/icons'
import { Button, Collapse, notification, Popconfirm } from 'antd'
import * as moment from 'moment'
import * as R from 'ramda'
import { useState } from 'react'

import { api } from '../../api'
import { orderStatusInvMap } from '../../utils'
import { HeaderLabel } from '../HeaderLabel'
import { Purchase } from './Purchase'

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
                        <HeaderLabel label="Order Date">{orderDate}</HeaderLabel>
                        <HeaderLabel label="Total Price">
                            {order.prices.total_price} {firstPurchase.currency ?? 'TL'}
                        </HeaderLabel>
                        <HeaderLabel label="Total Discount">
                            -{order.prices.discount} {firstPurchase.currency ?? 'TL'}
                        </HeaderLabel>
                        <HeaderLabel label="Delivery fee">
                            {order.prices.delivery_price} {firstPurchase.currency ?? 'TL'}
                        </HeaderLabel>
                        <HeaderLabel label="Receiver">
                            {[firstPurchase.address.name, firstPurchase.address.surname].filter(Boolean).join(' ')}
                        </HeaderLabel>
                        <HeaderLabel label="# of Products">{order.purchases.length}</HeaderLabel>
                        <div className="order-header-details">
                            {canCancel && (
                                <Popconfirm
                                    title="Cancel this order?"
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
                                Details
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
