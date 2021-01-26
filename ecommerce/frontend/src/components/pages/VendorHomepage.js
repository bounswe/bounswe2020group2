import { useAppContext } from '../../context/AppContext'
import React, { useEffect, useState } from 'react'
import { Spin, Button, notification } from 'antd'
import { VendorPageContent } from '../VendorContent/VendorPageContent'
import { formatProduct, formatSearchQueryParams } from '../../utils'
import { VendorSplash } from '../VendorContent/VendorSplash'
import { VendorReviews } from '../VendorContent/VendorReviews'
import { api } from '../../api'
import { Tabs } from 'antd'
import './VendorHomepage.less'
import { PlusCircleOutlined } from '@ant-design/icons'
import { ProductModal } from '../product_modal/ProductModal'

const { TabPane } = Tabs

export const VendorHomepage = props => {
    const { id } = props.match.params
    const vendorId = parseInt(id)
    const { user } = useAppContext()
    const isVendorAndOwner = user.type === 'vendor' && vendorId === user.id
    const [editable, setEditable] = useState(isVendorAndOwner)
    const [modalVisibility, setModalVisibility] = useState(false)
    const [modalProduct, setModalProduct] = useState(undefined)
    const [vendorProductRefreshKey, setVendorProductRefreshKey] = useState(0)

    const onProductChange = () => {
        setVendorProductRefreshKey(vendorProductRefreshKey + 1)
    }

    const onAddProduct = () => {
        setModalProduct(undefined)
        setModalVisibility(true)
    }

    const onSuccessModal = () => {
        setModalProduct(undefined)
        setModalVisibility(false)
        setVendorProductRefreshKey(vendorProductRefreshKey + 1)
        notification.success({ message: 'Successfully added a product.' })
    }

    const onCancelModal = () => {
        setModalProduct(undefined)
        setModalVisibility(false)
    }

    const onEditModeChange = checked => {
        setEditable(checked)
    }

    const addProductButton = (
        <Button type="primary" icon={<PlusCircleOutlined />} onClick={onAddProduct}>
            Add Product
        </Button>
    )

    return (
        <div className="vendor-homepage">
            <VendorSplash vendorId={vendorId} editable={editable} onEditModeChange={onEditModeChange} />
            <ProductModal
                mode={'add'}
                visible={modalVisibility}
                product={modalProduct}
                onSuccess={onSuccessModal}
                onCancel={onCancelModal}
            />
            <div>
                <Tabs type="card" tabBarExtraContent={isVendorAndOwner && editable ? addProductButton : null}>
                    <TabPane tab="Products" key="vendor-products">
                        <VendorMainContent
                            key={vendorProductRefreshKey}
                            vendorId={vendorId}
                            editable={editable}
                            onChange={onProductChange}
                        />
                    </TabPane>
                    <TabPane tab="Reviews" key="vendor-reviews">
                        <VendorReviews vendorId={vendorId} />
                    </TabPane>
                </Tabs>
            </div>
        </div>
    )
}

const VendorMainContent = ({ editable, vendorId, onChange }) => {
    const [products, setProducts] = useState([])
    const [isLoading, setIsLoading] = useState(true)

    useEffect(() => {
        async function fetch() {
            try {
                setIsLoading(true)
                const {
                    data: {
                        data: { pagination, products },
                    },
                } = await api.post(`/search/products`, {
                    vendor: vendorId,
                    sort_by: 'arrival',
                    sort_order: 'decreasing',
                    page_size: 100,
                })
                setProducts(products.map(formatProduct))
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
                <VendorPageContent products={products} editable={editable} onChange={onChange} />
            </div>
        </Spin>
        // Pagination
    )
}
