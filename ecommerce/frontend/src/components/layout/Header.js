import { Layout, Menu } from 'antd'
import { Link } from 'react-router-dom'

export const Header = () => {
    return (
        <Layout.Header className="header">
            <div className="header-logo">
                <Link to="/">GETFLIX</Link>
            </div>
            <Menu className="header-menu" mode="horizontal">
                <Menu.Item key="login">
                    <Link to="/login">Login</Link>
                </Menu.Item>
                <Menu.Item key="signup">
                    <Link to="/signup">Sign up</Link>
                </Menu.Item>
            </Menu>
        </Layout.Header>
    )
}
