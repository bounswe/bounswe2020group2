import './Layout_common.less'

import {
    ShopOutlined,
    AppstoreOutlined,
    CreditCardOutlined,
    HomeOutlined,
    LogoutOutlined,
    MessageOutlined,
    ProfileOutlined,
    ShoppingCartOutlined,
    UserOutlined,
    BellOutlined,
    FolderOpenOutlined,
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
    const isVendor = user.type === 'vendor'
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
                <Menu.Item key="lists" icon={<FolderOpenOutlined />}>
                    Lists
                </Menu.Item>
                <Menu.Item key="logout" icon={<LogoutOutlined />}>
                    Logout
                </Menu.Item>
            </Menu>
        )
    }

    return (
        <div className="header-customer">
            {!isVendor && (
                <Link to="/shoppingCart" className="header-customer-cart">
                    <Button
                        className="header-customer-cart"
                        icon={<ShoppingCartOutlined className="header-customer-cart-icon" />}
                        ghost>
                        My Cart
                    </Button>
                </Link>
            )}
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
        } else if (key === 'homepage') {
            history.push({ pathname: `/vendor/${user.id}` })
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
                <Menu.Item key="homepage" icon={<ShopOutlined />}>
                    Shop
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
            <div className="header-vendor-quicknavigation">
                <Link to="/profile/orders" className="header-vendor-orders">
                    <Button
                        className="header-vendor-orders"
                        icon={<AppstoreOutlined className="header-vendor-orders-icon" />}
                        ghost>
                        Orders
                    </Button>
                </Link>
                <Link to={`/vendor/${user.id}`} className="header-vendor-orders">
                    <Button
                        className="header-vendor-orders"
                        icon={<ShopOutlined className="header-vendor-orders-icon" />}
                        ghost>
                        My Shop
                    </Button>
                </Link>
            </div>
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
