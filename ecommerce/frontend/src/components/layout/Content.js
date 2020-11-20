import './Layout_common.less'
import { Route, Switch, Link } from 'react-router-dom'
import { Layout } from 'antd'
import { LoginPage } from '../pages/LoginPage'
import { SignupPage } from '../pages/SignupPage'
import { HomePage } from '../pages/HomePage'
import { ProfilePage } from '../pages/ProfilePage'

export const Content = () => {
    return (
        <Layout.Content className="content">
            <Switch>
                <Route path="/login">
                    <LoginPage />
                </Route>
                <Route path="/signup">
                    <SignupPage />
                </Route>
                <Route path="/profile">
                    <ProfilePage />
                </Route>
                <Route path="/">
                    <HomePage />
                </Route>
            </Switch>
        </Layout.Content>
    )
}
