import './ProductImageGallery.less'

import { Pagination, Rate, Skeleton } from 'antd'
import React, { useState } from 'react'
import cls from 'classnames'

export const ProductImageGallery = ({ product, loading = false }) => {
    const [active, setActive] = useState(0)

    const getImages = () => {
        if (loading) return []

        // this needs to be updated when the json for product endpoint is clear
        return product.images

        // right now using mock photos for testing
        // return [
        //     product.image_url,
        //     'https://picsum.photos/id/237/200/300',
        //     'https://picsum.photos/id/102/400/500',
        //     'https://picsum.photos/id/27/200/300',
        //     'https://picsum.photos/id/72/600/300',
        // ]
    }

    const images = getImages()

    const onDetailImageClick = ix => () => {
        setActive(ix)
    }

    if (loading) {
        return (
            <div className="product-image-gallery">
                <div className="product-image-gallery-side">
                    <Skeleton.Avatar className="product-image-gallery-detail" shape="square" active />
                    <Skeleton.Avatar className="product-image-gallery-detail" shape="square" active />
                    <Skeleton.Avatar className="product-image-gallery-detail" shape="square" active />
                    <Skeleton.Avatar className="product-image-gallery-detail" shape="square" active />
                </div>
                <div className="product-image-gallery-main-container">
                    <Skeleton.Image className="product-image-gallery-main" shape="square" active />
                </div>
            </div>
        )
    }

    const onPaginationChange = newPage => {
        setActive(newPage - 1)
    }

    return (
        <div className="product-image-gallery">
            <div className="product-image-gallery-side">
                {images.map((img, ix) => {
                    return (
                        <img
                            key={ix}
                            onClick={onDetailImageClick(ix)}
                            className={cls('product-image-gallery-detail', {
                                'product-image-gallery-detail__selected': ix == active,
                            })}
                            src={img}
                        />
                    )
                })}
            </div>
            <div className="product-image-gallery-main-container">
                <img className="product-image-gallery-main" src={images[active]} />
                <div className="product-image-gallery-main-pagination">
                    <Pagination
                        size="small"
                        total={images.length}
                        pageSize={1}
                        current={active + 1}
                        onChange={onPaginationChange}
                    />
                </div>
            </div>
        </div>
    )
}
