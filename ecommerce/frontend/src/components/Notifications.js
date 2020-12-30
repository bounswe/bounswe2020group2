import './Notifications.less'
import { useAppContext } from '../context/AppContext'
import { useEffect, useState } from 'react'
import { Spin } from 'antd'
import { formatProduct, productSortBy, sleep } from '../utils'
import { api } from '../api'
import { format } from 'prettier'
import moment from 'moment'
import { Link } from 'react-router-dom'

import { notifications } from '../mocks/mocks'
import './Notifications.less'

export const Notifications = () => {
    const [notifications, setNotifications] = useState([])

    useEffect(async () => {
        try {
            const { data } = await api.get('/notifications')
            console.log(data)
            setNotifications(data)
        } catch (error) {
            console.error(error)
        }
    }, [])
    return (
        <div className="notifications-container">
            {notifications.map(n => {
                return (
                    <div>
                        <p>{n.message}</p>
                        {moment(n.date).format('LL')}
                        <Link to={`/product/${n.product.id}`}>See</Link>
                    </div>
                )
            })}
        </div>
    )
}
