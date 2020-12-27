import './ProfilePage.less'
import { ProfileDetails } from '../profile/ProfileDetails'
import { ProfileContent } from '../profile/ProfileContent'
import { useAppContext } from '../../context/AppContext'
import { Tabs, Radio, Space } from 'antd'
import { OrdersList } from '../OrdersList'
const { TabPane } = Tabs

export const ProfilePage = () => {
    const { user } = useAppContext()

    return (
        <div className="profile-page-wrapper">
            <Tabs tabPosition="left" defaultActiveKey="orders">
                <TabPane tab="Update Profile" key="update-profile">
                    <div className="right-bar-profile-content">
                        <ProfileContent key={user.id} user={user} />
                    </div>
                </TabPane>
                <TabPane tab="Orders" key="orders">
                    <OrdersList />
                </TabPane>
                <TabPane tab="Cards" key="cards">
                    TO DO: Cards to be implemented
                </TabPane>
                <TabPane tab="Addresses" key="addresses">
                    TO DO: Addresses to be implemented
                </TabPane>
                <TabPane tab="Messages" key="messages">
                    TO DO: Messages to be implemented
                </TabPane>
            </Tabs>
        </div>
    )
}
