import './Layout_common.less'
import { Route, Switch, Link } from 'react-router-dom'
import { Layout } from 'antd'
import { LoginPage } from '../pages/LoginPage'
import { SignupPage } from '../pages/SignupPage'
import { HomePage } from '../pages/HomePage'
import { ShoppingCartPage } from '../ShoppingCart/ShoppingCartPage'
import { SearchPage } from '../pages/SearchPage'

export const Content = () => {
    return (
        <Layout.Content className="content">
            <Switch>
                <Route path="/login" component={LoginPage} />
                <Route path="/shoppingCart" component={ShoppingCartPage} />
                <Route path="/signup" component={SignupPage} />
                <Route path="/search/:type" component={SearchPage} />
                <Route path="/" component={HomePage} />
            </Switch>
        </Layout.Content>
    )
}
