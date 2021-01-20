import { useAppContext } from '../../context/AppContext'
import React, { useEffect, useState } from 'react'
import { Spin, Button } from 'antd'
import { UserReviews } from '../UserReview/UserReviews'
import { VendorPageContent } from '../VendorContent/VendorPageContent'
import { formatProduct, formatSearchQueryParams } from '../../utils'
import { VendorSplash } from '../VendorContent/VendorSplash'
import { api } from '../../api'
import { Tabs } from 'antd'
import './VendorHomepage.less'
import { PlusCircleOutlined } from '@ant-design/icons'

const { TabPane } = Tabs

export const VendorHomepage = props => {
    const { vendorId } = props.match.params
    const [editMode, setEditMode] = useState(true)
    const { user } = useAppContext()
    const isVendorAndOwner = user.type === 'vendor' && vendorId === user.id.toString()

    const onEditModeChange = checked => {
        setEditMode(checked)
    }

    const onAddProduct = () => {
        console.log('Adding product here')
    }
    const addProductButton = (
        <Button type="primary" icon={<PlusCircleOutlined />} onClick={onAddProduct}>
            Add Product
        </Button>
    )

    return (
        <div className="vendor-homepage">
            <VendorSplash vendorId={vendorId} editable={editMode} onEditModeChange={onEditModeChange} />
            <div>
                <Tabs onChange={callback} type="card" tabBarExtraContent={isVendorAndOwner ? addProductButton : null}>
                    <TabPane tab="Products" key="vendor-products">
                        <VendorMainContent editMode={editMode} />
                    </TabPane>
                    <TabPane tab="Reviews" key="vendor-reviews">
                        <UserReviews productId={1} />
                    </TabPane>
                </Tabs>
            </div>
        </div>
    )
}

const VendorMainContent = ({ editMode }) => {
    const [trendingProducts, setTrendingProducts] = useState([])
    const [isLoading, setIsLoading] = useState(true)

    useEffect(() => {
        async function fetch() {
            try {
                setIsLoading(true)
                const {
                    data: {
                        data: { pagination, products },
                    },
                } = await api.post(`/search/products`, {})
                console.log(products)
                setTrendingProducts(products.map(formatProduct))
            } catch (error) {
                console.error('failed to load trending products', error)
            } finally {
                setIsLoading(false)
            }
        }

        fetch()
    }, [])

    return (
        <Spin spinning={isLoading}>
            <div className="vendor-page-trending-grid-wrapper">
                <VendorPageContent products={trendingProducts} editMode={editMode} />
            </div>
        </Spin>
        // Pagination
    )
}
