import './Layout_common.less'

import { Layout } from 'antd'
import { Redirect, Route, Switch } from 'react-router-dom'

import { useAppContext } from '../../context/AppContext'
import { CategoryBar } from '../CategoryBar'
import { CheckoutPage } from '../pages/CheckoutPage'
import { HomePage } from '../pages/HomePage'
import { LoginPage } from '../pages/LoginPage'
import { ProductPage } from '../pages/ProductPage'
import { ProfilePage } from '../pages/ProfilePage'
import { SearchPage } from '../pages/SearchPage'
import { SignupPage } from '../pages/SignupPage'
import { ShoppingCartPage } from '../ShoppingCart/ShoppingCartPage'

export const Content = () => {
    const { user } = useAppContext()
    const isGuest = user.type === 'guest'
    const isCustomer = user.type === 'customer'
    const isVendor = user.type === 'vendor'
    const isUser = !isGuest

    return (
        <Layout.Content className="content">
            <CategoryBar />
            <Switch>
                <Route path="/search/:type" component={SearchPage} />
                <Route path="/product/:productId" component={ProductPage} />
                {isGuest && <Route path="/login" component={LoginPage} />}
                {isCustomer && <Route path="/checkout" component={CheckoutPage} />}
                {isCustomer && <Route path="/shoppingCart" component={ShoppingCartPage} />}
                {isGuest && <Route path="/signup" component={SignupPage} />}
                <Route path="/profile" component={ProfilePage} />
                <Route exact path="/" component={HomePage} />
                <Redirect to="/" />
            </Switch>
        </Layout.Content>
    )
}
