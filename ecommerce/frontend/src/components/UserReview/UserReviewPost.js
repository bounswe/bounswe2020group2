import React, { useEffect, useState } from 'react'
import { Spin, Button, Input, Rate, notification } from 'antd'
import { Link } from 'react-router-dom'
import './UserReviews.less'
import { api } from '../../api'
import './UserReviewPost.less'
import { useAppContext } from '../../context/AppContext'
const { TextArea } = Input
export const UserReviewPost = ({
    width = '500px',
    product = {
        id: 1,
        name: "Under Armour Men's Tech 2.0 Short Sleeve T-Shirt",
        price: 18,
        creation_date: '2019-08-20T07:22:34Z',
        total_rating: 80,
        rating_count: 20,
        stock_amount: 10,
        short_description: '100% Polyester, Machine Washable, Material wicks sweat & dries really fast',
        subcategory: {
            name: "Men's Fashion",
            id: 10,
        },
        long_description: 'long description',
        discount: 0.1,
        category: {
            id: 4,
            name: 'Clothing',
        },
        brand: {
            name: 'Under Armour',
            id: 1,
        },
        vendor: {
            rating: 4.666666666666667,
            id: 1,
            name: 'omerfaruk deniz',
        },
        rating: 4,
        images: [
            'https://underarmour.scene7.com/is/image/Underarmour/V5-1345317-014_FC?rp=standard-0pad|pdpMainDesktop&scl=1&fmt=jpg&qlt=85&resMode=sharp2&cache=on,on&bgc=F0F0F0&wid=566&hei=708&size=566,708',
            'https://underarmour.scene7.com/is/image/Underarmour/V5-1326413-001_FC_Main?rp=standard-0pad|pdpMainDesktop&scl=1&fmt=jpg&qlt=85&resMode=sharp2&cache=on,on&bgc=F0F0F0&wid=566&hei=708&size=566,708',
            'https://images-na.ssl-images-amazon.com/images/I/51tb0KkLYqL._AC_UX679_.jpg',
        ],
        old_price: 20,
    },
}) => {
    const [isLoading, setIsLoading] = useState(false)
    const [comment, setComment] = useState('')
    const [rating, setRating] = useState(null)
    const { user: user_id } = useAppContext()
    const onChangeComment = e => {
        setComment(e.target.value)
    }
    const onChangeRating = e => {
        setRating(e)
    }
    const onPressReview = async () => {
        if (!rating || comment == '') {
            if (comment == '') {
                notification.warning({ description: 'Please write a comment' })
            }
            if (!rating) {
                notification.warning({ description: 'Please rate the product' })
            }
        } else {
            try {
                setIsLoading(true)
                const { data } = await api.post(`/review`, {
                    user_id: user_id,
                    product_id: product.id,
                    vendor_id: product.vendor.id,
                    rating: rating,
                    comment: comment,
                })
                console.log('data', data)
            } catch (error) {
                if (error.response.status === 403) {
                    notification.warning({ description: 'You have to buy the product to make a comment' })
                }
            } finally {
                setIsLoading(false)
            }

            // notification.success({ description: 'Successfully reviewed' })
        }
    }
    return (
        <div className="post-review-product" style={{ maxWidth: width }}>
            <div className="post-review-item">
                <div className="cart-item-picture">
                    <Link to={`/product/${product.id}`}>
                        <img
                            alt={product.name}
                            width={'100%'}
                            src={product.images[0] ?? 'https://picsum.photos/300'}></img>
                    </Link>
                </div>
                <div className="post-review-description">
                    <div className="post-review-title">
                        <Link to={`/product/${product.id}`}>{product.name ?? 'title'}</Link>
                    </div>
                    <div className="post-review-short-description">{product.short_description}</div>
                    <div className="post-review-rate">
                        <Rate onChange={onChangeRating} />
                    </div>
                </div>
            </div>
            <hr />
            <div>
                <h3>Your comment</h3>
                <TextArea
                    showCount
                    rows={4}
                    maxLength={500}
                    value={comment}
                    placeholder="Did you like the product?"
                    onChange={onChangeComment}
                />
            </div>
            <div className="post-review-button">
                <Spin spinning={isLoading}>
                    <Button type="primary" onClick={onPressReview}>
                        Send Review
                    </Button>
                </Spin>
            </div>
        </div>
    )
}
