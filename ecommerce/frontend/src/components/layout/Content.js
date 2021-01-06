import './Layout_common.less'

import { Layout } from 'antd'
import { Redirect, Route, Switch, useLocation } from 'react-router-dom'

import { useAppContext } from '../../context/AppContext'
import { CategoryBar } from '../CategoryBar'
import { EmailVerification } from '../EmailVerification'
import { IsVerifiedNotification } from '../IsVerifiedNotification'
import { OrdersList } from '../order/OrdersList'
import { CheckoutPage } from '../pages/CheckoutPage'
import { HomePage } from '../pages/HomePage'
import { LoginPage } from '../pages/LoginPage'
import { ProductPage } from '../pages/ProductPage'
import { ProfilePage } from '../pages/ProfilePage'
import { SearchPage } from '../pages/SearchPage'
import { ShoppingCartPage } from '../pages/ShoppingCartPage'
import { SignupPage } from '../pages/SignupPage'
import { VendorHomepage } from '../pages/VendorHomepage'

export const Content = () => {
    const { user } = useAppContext()
    const location = useLocation()
    const isGuest = user.type === 'guest'
    const isCustomer = user.type === 'customer'
    const isVendor = user.type === 'vendor'
    const isUser = !isGuest

    return (
        <Layout.Content className="content">
            <CategoryBar />
            {location.pathname.startsWith('/profile') || <IsVerifiedNotification />}

            <Switch>
                {isUser && <Route path="/verify/:id" component={EmailVerification} />}
                <Route path="/search/:type" component={SearchPage} />
                <Route path="/product/:productId" component={ProductPage} />
                {isGuest && <Route path="/login" component={LoginPage} />}
                {isCustomer && <Route path="/checkout" component={CheckoutPage} />}
                {isCustomer && <Route path="/shoppingCart" component={ShoppingCartPage} />}
                {isGuest && <Route path="/signup" component={SignupPage} />}
                {isUser && <Route path="/profile/:section" component={ProfilePage} />}
                {isUser && <Route path="/profile/" component={ProfilePage} />}
                <Route exact path="/" component={HomePage} />
                <Route to="/vendor" component={VendorHomepage} />
                <Redirect to="/" />
            </Switch>
        </Layout.Content>
    )
}
