import './Layout_common.less'
import { Layout } from 'antd'
import { Header } from './Header'
import { Content } from './Content'
import { Footer } from './Footer'

export const MainLayout = () => {
    return (
        <Layout>
            <Header />
            <Content />
            <Footer />
        </Layout>
    )
}
