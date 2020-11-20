import './Layout_common.less'
import { Route, Switch, Link } from 'react-router-dom'
import { Layout } from 'antd'
import { LoginPage } from '../pages/LoginPage'
import { SignupPage } from '../pages/SignupPage'
import { HomePage } from '../pages/HomePage'
import { ShoppingCart } from '../ShoppingCart/ShoppingCart'

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
                <Route path="/shopping">
                    <ShoppingCart />
                </Route>
                <Route path="/">
                    <HomePage />
                </Route>
            </Switch>
        </Layout.Content>
    )
}
