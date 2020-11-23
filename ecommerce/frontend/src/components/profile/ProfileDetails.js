import './ProfileDetails.less'
import { Link } from 'react-router-dom'
import { Tabs } from 'antd'

const { TabPane } = Tabs

export const ProfileDetails = () => {
    return (
        <div className="profile-details">
            <Tabs className="profile-tabpane" tabPosition="left" type="card" defaultActiveKey="4" centered>
                <TabPane tab={<Link to="/profile/change-password">Change Password</Link>} key="1" />
                <TabPane tab={<Link to="/profile/purchases">Purchase History</Link>} key="2" />
                <TabPane tab={<Link to="/profile/messages">Messages</Link>} key="3" />
                <TabPane tab="" key="4" disabled />
            </Tabs>
        </div>
    )
}
