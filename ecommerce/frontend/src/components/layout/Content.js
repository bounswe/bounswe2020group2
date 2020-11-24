import './Layout_common.less'
import { Route, Switch, Link } from 'react-router-dom'
import { Layout } from 'antd'
import { LoginPage } from '../pages/LoginPage'
import { SignupPage } from '../pages/SignupPage'
import { HomePage } from '../pages/HomePage'
import { ProfilePage } from '../pages/ProfilePage'
import { SearchPage } from '../pages/SearchPage'
import { ProductPage } from '../pages/ProductPage'

export const Content = () => {
    return (
        <Layout.Content className="content">
            <Switch>
                <Route path="/login" component={LoginPage} />
                <Route path="/signup" component={SignupPage} />
                <Route path="/profile" component={ProfilePage} />
                <Route path="/search/:type" component={SearchPage} />
                <Route path="/product/:productId" component={ProductPage} />
                <Route path="/" component={HomePage} />
            </Switch>
        </Layout.Content>
    )
}
