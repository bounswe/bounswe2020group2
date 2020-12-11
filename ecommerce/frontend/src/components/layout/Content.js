import './Layout_common.less'

import { Layout } from 'antd'
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
import { UserReview } from '../UserReview'

export const Content = () => {
    return (
        <Layout.Content className="content">
            <CategoryBar />
            <Switch>
                <Route path="/review" component={UserReview} />
                <Route path="/login" component={LoginPage} />
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
