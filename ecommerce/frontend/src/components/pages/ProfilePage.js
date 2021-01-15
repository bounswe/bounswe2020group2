import './ProfilePage.less'

import { Alert, Tabs } from 'antd'

import { useAppContext } from '../../context/AppContext'
import { AddressList } from '../addresslist/AddressList'
import { Conversations } from '../messages/Conversations'
import { CreditCardList } from '../cardlist/CreditCardList'
import { OrdersList } from '../order/OrdersList'
import { ProfileContent } from '../profile/ProfileContent'
import { ChatContextProvider } from '../../context/ChatContext'

const { TabPane } = Tabs

export const ProfilePage = props => {
    const { user } = useAppContext()

    const tabKey = props.match.params.section ?? 'update-profile'

    const onTabChange = tabKey => {
        const path = tabKey === 'update-profile' ? '/profile' : `/profile/${tabKey}`
        props.history.replace(path)
    }

    return (
        <div className="profile-page-wrapper">
            <Tabs tabPosition="left" activeKey={tabKey} onChange={onTabChange}>
                <TabPane tab="Update Profile" key="update-profile" forceRender>
                    <Alert.ErrorBoundary>
                        <div className="right-bar-profile-content">
                            <ProfileContent key={user.id} user={user} />
                        </div>
                    </Alert.ErrorBoundary>
                </TabPane>
                <TabPane tab="Orders" key="orders" forceRender>
                    <Alert.ErrorBoundary>TODO: will be adapted to vendor</Alert.ErrorBoundary>
                </TabPane>
                {user.type === 'customer' && (
                    <TabPane tab="Cards" key="cards" forceRender>
                        <Alert.ErrorBoundary>
                            <CreditCardList />
                        </Alert.ErrorBoundary>
                    </TabPane>
                )}
                <TabPane tab="Addresses" key="addresses" forceRender>
                    <Alert.ErrorBoundary>
                        <AddressList />
                    </Alert.ErrorBoundary>
                </TabPane>
                <TabPane tab="Messages" key="messages" forceRender>
                    <Alert.ErrorBoundary>
                        <ChatContextProvider>
                            <Conversations />
                        </ChatContextProvider>
                    </Alert.ErrorBoundary>
                </TabPane>
            </Tabs>
        </div>
    )
}
