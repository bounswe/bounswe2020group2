import { notification } from 'antd'
import { useEffect, useState } from 'react'
import { api } from '../api'
import { formatOrder } from '../utils'
import './PastOrdersList.less'

export const PastOrdersList = () => {
    const [orders, setOrders] = useState([])
    const [isLoading, setIsLoading] = useState(false)
    const fetchOrders = async () => {
        setIsLoading(true)
        try {
            const {
                data: { status, orders },
            } = await api.get(`/customer/orders`)

            if (status.successful) {
                setOrders(orders.map(formatOrder))
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
        fetchOrders()
    }, [])

    return (
        <div className="orders-container">
            <div className="orders-header">Order History</div>
            <div className="orders-content">
                {orders.map(order => {
                    return JSON.stringify(order)
                })}
            </div>
        </div>
    )
}
