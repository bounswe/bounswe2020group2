import './ProfilePage.less'
import { ProfileDetails } from '../profile/ProfileDetails'
import { ProfileContent } from '../profile/ProfileContent'
import { useAppContext } from '../../context/AppContext'
import { Tabs, Radio, Space } from 'antd';
const { TabPane } = Tabs;

export const ProfilePage = () => {
    // future version
    const { user } = useAppContext()
    return (
        <div className="profile-page-wrapper">
            <Tabs tabPosition="top">
                <TabPane tab="Update Profile" key="update-profile">
                <div className="right-bar-profile-content">
                    <ProfileContent key={user.id} user={user} />
                </div>
                </TabPane>
                <TabPane tab="Purchase History" key="purchase-history">
                    TO DO: Purchase History to be implemented
                </TabPane>
                <TabPane tab="Messages" key="messages">
                    TO DO: Messages to be implemented
                </TabPane>
            </Tabs>
        </div>
    )
}
