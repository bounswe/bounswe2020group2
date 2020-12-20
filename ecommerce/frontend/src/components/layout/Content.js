import './Layout_common.less'

import { Layout, Alert } from 'antd'
import { Route, Switch } from 'react-router-dom'

import { HomePage } from '../pages/HomePage'
import { LoginPage } from '../pages/LoginPage'
import { ProductPage } from '../pages/ProductPage'
import { ProfilePage } from '../pages/ProfilePage'
import { SearchPage } from '../pages/SearchPage'
import { SignupPage } from '../pages/SignupPage'
import { ShoppingCartPage } from '../ShoppingCart/ShoppingCartPage'
import { CategoryBar } from '../CategoryBar'
import { CheckoutPage } from '../pages/CheckoutPage'
import { EmailVerification } from '../EmailVerification'
import { useAppContext } from '../../context/AppContext'

export const Content = () => {
    const { user } = useAppContext()
    return (
        <Layout.Content className="content">
            <CategoryBar />
            {user.type === 'guest' || user.is_verified ? null : (
                <Alert message="Please verify your account." type="warning" showIcon closable />
            )}
            <Switch>
                <Route path="/login" component={LoginPage} />
                <Route path="/verify/:id" component={EmailVerification} />
                <Route path="/checkout" component={CheckoutPage} />
                <Route path="/shoppingCart" component={ShoppingCartPage} />
                <Route path="/signup" component={SignupPage} />
                <Route path="/profile" component={ProfilePage} />
                <Route path="/search/:type" component={SearchPage} />
                <Route path="/product/:productId" component={ProductPage} />
                <Route path="/" component={HomePage} />
            </Switch>
        </Layout.Content>
    )
}
