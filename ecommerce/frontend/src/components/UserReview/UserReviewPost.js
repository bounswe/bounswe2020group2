import React, { useEffect, useState } from 'react'
import { Pagination, Spin, Button, Input, Rate, notification } from 'antd'
import { Link } from 'react-router-dom'
import './UserReviews.less'
import { api } from '../../api'
import './UserReviewPost.less'
import { product } from '../../mocks/mocks'
import { round } from '../../utils'
const { TextArea } = Input
export const UserReviewPost = (width = '500px') => {
    const [isLoading, setIsLoading] = useState(true)
    const [comment, setComment] = useState('')
    const [rating, setRating] = useState(null)
    const onChange = e => {
        // setComment(value)
        console.log(e)
    }
    const onPressReview = () => {
        if (!rating) {
            notification.warning({ description: 'Please rate the product' })
        }
        if (comment == '') {
            notification.warning({ description: 'Please comment the product' })
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
                    <div className="cart-item-title">
                        <Link to={`/product/${product.id}`}>{product.name ?? 'title'}</Link>
                    </div>
                    <div className="cart-item-description">{product.short_description}</div>
                    <div className="post-review-rate">
                        {'Please rate this product'}
                        <Rate />
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
                    placeholder="Did you like the product?"
                    onChange={onChange}
                />
            </div>
            <Button type="primary" onClick={onPressReview}>
                Send Review
            </Button>
        </div>
    )
}
