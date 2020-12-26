import { Button, Collapse } from 'antd'
import './Order.less'
import { Purchase } from './Purchase'
import * as R from 'ramda'
import { orderStatusInvMap } from '../utils'
import * as moment from 'moment'

const HorizontalLabel = ({ label, children }) => {
    return (
        <div>
            <span className="order-header-label">{label}:&nbsp;</span>
            <br />
            <span className="order-header-text">{children}</span>
        </div>
    )
}

export const Order = ({ order }) => {
    const onCancelOrder = async event => {
        event.stopPropagation() // prevent click on collapse
        // todo
    }

    const onShowOrderDetails = async event => {
        event.stopPropagation() // click on collapse
        // todo
    }

    const firstPurchase = order.order_all_purchase[0]

    const totalPrice =
        order.total_price ?? order.purchases.map(purchase => purchase.unit_price * purchase.amount).reduce(R.add, 0)

    const canCancel =
        true ||
        (R.any(purchase => orderStatusInvMap[purchase.status] !== 'cancelled', order.purchases) &&
            R.none(purchase => orderStatusInvMap[purchase.status] !== 'at_cargo', order.purchases) &&
            R.none(purchase => orderStatusInvMap[purchase.status] !== 'delivered', order.purchases))

    const orderDate = moment.utc(firstPurchase.purchase_date).format('HH:mm DD/MM/YYYY')
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
                                <Button danger onClick={onCancelOrder}>
                                    Cancel Order{' '}
                                </Button>
                            )}
                            &nbsp;
                            <Button onClick={onShowOrderDetails}>Show Order Details</Button>
                        </div>
                    </div>
                }>
                <div className="order-purchases">
                    {order.purchases.map(purchase => {
                        return <Purchase key={order.id} purchase={purchase} />
                    })}
                </div>
            </Collapse.Panel>
        </Collapse>
    )
}
