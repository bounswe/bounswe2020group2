import './Order.less'
import { Purchase } from './Purchase'

const HorizontalLabel = ({ label, children }) => {
    return (
        <div>
            <span className="order-header-label">{label}</span>
            <span className="order-header-text">{children}</span>
        </div>
    )
}

export const Order = ({ order }) => {
    const onCancelOrder = async () => {
        // todo
    }

    const firstPurchase = order.order_all_purchase[0]

    return (
        <div className="order-container">
            <div className="order-header">
                <HorizontalLabel label="Order Date:">{firstPurchase.purchase_date}</HorizontalLabel>
                <HorizontalLabel label="Order Date:">{firstPurchase.purchase_date}</HorizontalLabel>
                <HorizontalLabel label="Receiver:">
                    {[firstPurchase.name, firstPurchase.surname].filter(Boolean).join(' ')}}
                </HorizontalLabel>
                <div>Show order details</div>
            </div>
            <div className="order-purchases">
                {order.purchases.map(purchase => {
                    return <Purchase key={order.id} purchase={purchase} />
                })}
            </div>
        </div>
    )
}
