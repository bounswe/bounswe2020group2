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
    useEffect(() => {
        const fetch = async () => {
            const { data } = await api.get('/notifications')
            console.log(data)
            setNotifications(data)
        }
        try {
            fetch()
        } catch (error) {
            console.error(error)
        }
    }, [])

    return (
        <div className="notifications-container">
            {notifications.map(n => {
                return (
                    <div key={n.id} className="notification-item">
                        <div className="notification-image">
                            <img src={n.product.images[0]} />
                        </div>
                        <div className="notification-message">
                            <div>
                                <p>{n.message}</p>
                                <Link to={`/product/${n.product.id}`}>See new deal now. </Link>{' '}
                            </div>
                            <div className="notification-date">{moment(n.date).fromNow()}</div>
                        </div>
                    </div>
                )
            })}
        </div>
    )
}
