import { useAppContext } from '../../context/AppContext'
import React, { useEffect, useState } from 'react'
import { Spin } from 'antd'
import { UserReviews } from '../UserReview/UserReviews'
import { VendorPageContent } from '../VendorContent/VendorPageContent'
import { formatProduct, formatSearchQueryParams } from '../../utils'
import { VendorSplash } from '../VendorContent/VendorSplash'
import { api } from '../../api'
import { Tabs } from 'antd'
import './VendorHomepage.less'

const { TabPane } = Tabs
function callback(key) {
    console.log(key)
}

export const VendorHomepage = props => {
    const { vendorId } = props.match.params
    const [editMode, setEditMode] = useState(true)

    const onEditModeChange = checked => {
        setEditMode(checked)
    }

    return (
        <div>
            <VendorSplash vendorId={vendorId} editable={editMode} onEditModeChange={onEditModeChange} />
            <div style={{ margin: '32px 64px 0 64px' }}>
                <Tabs onChange={callback} type="card">
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
