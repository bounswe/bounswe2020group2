import './OrdersList.less'

import { notification, Spin } from 'antd'
import { useEffect, useState } from 'react'

import { api } from '../../api'
import { useAppContext } from '../../context/AppContext'
import { formatOrder, formatPurchase } from '../../utils'
import { Order } from './Order'
import { Purchase } from './Purchase'

export const OrdersList = () => {
    const [orders, setOrders] = useState([])
    const [isLoading, setIsLoading] = useState(false)
    const { user } = useAppContext()

    const fetchCustomerOrders = async () => {
        setIsLoading(true)
        try {
            const {
                data: { status, orders },
            } = await api.get(`/customer/orders`)

            if (status.successful) {
                const sortedOrder = orders.map(formatOrder)
                sortedOrder.reverse()

                setOrders(sortedOrder)
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

    const fetchVendorOrders = async () => {
        // todo
        setIsLoading(true)
        try {
            const response = await api.get(`/vendor/order`)
            console.log('/vendor/order', response.data)
            const { status, orders } = response.data

            if (status.successful) {
                const sortedOrder = orders.map(formatPurchase)
                sortedOrder.reverse()

                setOrders(sortedOrder)
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
    const fetch = async () => {
        if (user.type === 'customer') fetchCustomerOrders()
        else if (user.type === 'vendor') fetchVendorOrders()
    }

    const onOrderCancelled = fetch

    useEffect(() => {
        fetch()
    }, [])

    return (
        <div className="orders-container">
            <div className="orders-header">Order History</div>
            <Spin spinning={isLoading}>
                <div className="orders-content">
                    {orders.map(order => {
                        if (user.type === 'customer')
                            return <Order key={order.id} order={order} onOrderCancelled={onOrderCancelled} />

                        return <Purchase key={order.id} purchase={order} />
                    })}
                </div>
            </Spin>
        </div>
    )
}
