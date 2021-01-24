import './Layout_common.less'

import {
    AppstoreOutlined,
    CreditCardOutlined,
    HomeOutlined,
    LogoutOutlined,
    MessageOutlined,
    ProfileOutlined,
    ShoppingCartOutlined,
    UserOutlined,
    BellOutlined,
} from '@ant-design/icons'
import { Avatar, Button, Dropdown, Layout, Menu } from 'antd'
import { Link, useHistory } from 'react-router-dom'

import { useAppContext } from '../../context/AppContext'
import { SearchInputWrapper } from '../search/SearchInputWrapper'

export const GuestHeaderContent = () => {
    return (
        <Menu className="header-menu" mode="horizontal">
            <Menu.Item key="login">
                <Link to="/login">Login</Link>
            </Menu.Item>
            <Menu.Item key="signup">
                <Link to="/signup">Sign up</Link>
            </Menu.Item>
        </Menu>
    )
}

export const CustomerHeaderContent = () => {
    const { user, logout } = useAppContext()
    const history = useHistory()

    const onMenuItemClick = ({ key }) => {
        if (key === 'logout') {
            logout()
            history.push('/')
        } else if (key === 'update-profile') {
            history.push({ pathname: '/profile' })
        } else {
            // temp solution
            history.push({ pathname: `/profile/${key}` })
        }
    }

    const dropdownMenu = () => {
        return (
            <Menu onClick={onMenuItemClick}>
                <Menu.Item key="update-profile" icon={<ProfileOutlined />}>
                    Profile Details
                </Menu.Item>
                <Menu.Item key="orders" icon={<AppstoreOutlined />}>
                    Orders
                </Menu.Item>
                <Menu.Item key="cards" icon={<CreditCardOutlined />}>
                    Cards
                </Menu.Item>
                <Menu.Item key="addresses" icon={<HomeOutlined />}>
                    Addresses
                </Menu.Item>
                <Menu.Item key="notifications" icon={<BellOutlined />}>
                    Notifications
                </Menu.Item>
                <Menu.Item key="messages" icon={<MessageOutlined />}>
                    Messages
                </Menu.Item>
                <Menu.Item key="logout" icon={<LogoutOutlined />}>
                    Logout
                </Menu.Item>
            </Menu>
        )
    }

    return (
        <div className="header-customer">
            <Link to="/shoppingCart" className="header-customer-cart">
                <Button
                    className="header-customer-cart"
                    icon={<ShoppingCartOutlined className="header-customer-cart-icon" />}
                    ghost>
                    My Cart
                </Button>
            </Link>
            <Dropdown overlay={dropdownMenu()} placement={'bottomRight'} trigger="click">
                <Button className="header-customer-info" ghost>
                    <Avatar shape="square" size="large" icon={<UserOutlined />} />
                    <div className="header-customer-info-details">
                        <div className="header-customer-info-details-name">
                            {user.name} {user.lastname}
                        </div>
                        <div className="header-customer-info-details-email">{user.email}</div>
                    </div>
                </Button>
            </Dropdown>
        </div>
    )
}

export const VendorHeaderContent = () => {
    const { user, logout } = useAppContext()
    const history = useHistory()

    const onMenuItemClick = ({ key }) => {
        if (key === 'logout') {
            logout()
            history.push('/')
        } else if (key === 'update-profile') {
            history.push({ pathname: '/profile' })
        } else {
            // temp solution
            history.push({ pathname: `/profile/${key}` })
        }
    }

    const dropdownMenu = () => {
        return (
            <Menu onClick={onMenuItemClick}>
                <Menu.Item key="update-profile" icon={<ProfileOutlined />}>
                    Profile Details
                </Menu.Item>
                <Menu.Item key="orders" icon={<AppstoreOutlined />}>
                    Orders
                </Menu.Item>
                <Menu.Item key="addresses" icon={<HomeOutlined />}>
                    Addresses
                </Menu.Item>
                <Menu.Item key="messages" icon={<MessageOutlined />}>
                    Messages
                </Menu.Item>
                <Menu.Item key="logout" icon={<LogoutOutlined />}>
                    Logout
                </Menu.Item>
            </Menu>
        )
    }

    return (
        <div className="header-customer">
            <Link to="/profile/orders" className="header-vendor-orders">
                <Button
                    className="header-vendor-orders"
                    icon={<AppstoreOutlined className="header-vendor-orders-icon" />}
                    ghost>
                    Orders
                </Button>
            </Link>
            <Dropdown overlay={dropdownMenu()} placement={'bottomRight'} trigger="click">
                <Button className="header-customer-info" ghost>
                    <Avatar shape="square" size="large" icon={<UserOutlined />} />
                    <div className="header-customer-info-details">
                        <div className="header-customer-info-details-name">
                            {user.name} {user.lastname}
                        </div>
                        <div className="header-customer-info-details-email">{user.email}</div>
                    </div>
                </Button>
            </Dropdown>
        </div>
    )
}

export const Header = () => {
    const { user } = useAppContext()

    return (
        <Layout.Header className="header">
            <div className="header-logo">
                <Link to="/">getflix</Link>
            </div>
            <div className="header-search-bar">
                <SearchInputWrapper />
            </div>
            {user.type === 'guest' && <GuestHeaderContent />}
            {user.type === 'customer' && <CustomerHeaderContent />}
            {user.type === 'vendor' && <VendorHeaderContent />}
        </Layout.Header>
    )
}
