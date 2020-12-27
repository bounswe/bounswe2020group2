import './ProfilePage.less'

import { Tabs } from 'antd'

import { useAppContext } from '../../context/AppContext'
import { AddressList } from '../addresslist/AddressList'
import { CreditCardList } from '../cardlist/CreditCardList'
import { OrdersList } from '../OrdersList'
import { ProfileContent } from '../profile/ProfileContent'

const { TabPane } = Tabs

export const ProfilePage = () => {
    const { user } = useAppContext()

    return (
        <div className="profile-page-wrapper">
            <Tabs tabPosition="left" defaultActiveKey="update-profile">
                <TabPane tab="Update Profile" key="update-profile">
                    <div className="right-bar-profile-content">
                        <ProfileContent key={user.id} user={user} />
                    </div>
                </TabPane>
                <TabPane tab="Orders" key="orders">
                    <OrdersList />
                </TabPane>
                <TabPane tab="Cards" key="cards">
                    <CreditCardList />
                </TabPane>
                <TabPane tab="Addresses" key="addresses">
                    <AddressList />
                </TabPane>
                <TabPane tab="Messages" key="messages">
                    TO DO: Messages to be implemented
                </TabPane>
            </Tabs>
        </div>
    )
}
