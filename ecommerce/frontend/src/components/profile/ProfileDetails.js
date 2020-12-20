import './ProfileDetails.less'
import { Link, Redirect } from 'react-router-dom'
import { useState } from 'react'
import { Button, Tabs, notification } from 'antd'
import { api } from '../../api'
import { useAppContext } from '../../context/AppContext'

const { TabPane } = Tabs

export const ProfileDetails = () => {
    const [isLoading, setIsLoading] = useState(false)
    const { user } = useAppContext()
    console.log('user', user)
    const onVerify = async () => {
        try {
            setIsLoading(true)
            const { data } = await api.post('/user/verify', {})
            console.log('data', data)
            if (data.successful) {
                notification.successful({
                    description: 'Successfully verified account',
                    placement: 'topRight',
                    duration: 2,
                })
                setUser({ ...user, is_verified: true })
            }
        } catch {
            notification.error({
                description: 'Failed to verify account',
                placement: 'topRight',
                duration: 2,
            })
        } finally {
            setIsLoading(false)
        }
    }
    return (
        <div className="profile-details">
            <Tabs className="profile-tabpane" tabPosition="left" type="card" defaultActiveKey="4" centered>
                <TabPane tab={<Link to="/profile/change-password">Change Password</Link>} key="1" />
                <TabPane tab={<Link to="/profile/purchases">Purchase History</Link>} key="2" />
                <TabPane tab={<Link to="/profile/messages">Messages</Link>} key="3" />
                <TabPane tab="" key="4" disabled />
            </Tabs>
            {/* Below will show when user.is_verified is false */}
            {!user.is_verified ? (
                <div className="profile-page-verify">
                    <Button type="link" onClick={onVerify}>
                        Verify My Account
                    </Button>
                </div>
            ) : null}
        </div>
    )
}
