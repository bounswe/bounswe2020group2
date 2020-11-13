import { Route, Switch, Link } from 'react-router-dom'
import { Layout } from 'antd'
import { LoginPage } from '../LoginPage'
import { SignupPage } from '../SignupPage'
import { HomePage } from '../HomePage'

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
                <Route path="/">
                    <HomePage />
                </Route>
            </Switch>
        </Layout.Content>
    )
}
