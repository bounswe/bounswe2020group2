import React, { useEffect, useState } from 'react'
import { Spin, Button, Input, Rate, notification } from 'antd'
import { Link } from 'react-router-dom'
import './UserReviews.less'
import { api } from '../../api'
import './UserReviewPost.less'
import { useAppContext } from '../../context/AppContext'
const { TextArea } = Input

export const UserReviewPost = ({ product, onFinish }) => {
    const [isLoading, setIsLoading] = useState(false)
    const [comment, setComment] = useState('')
    const [rating, setRating] = useState(null)
    const { user } = useAppContext()

    const onChangeComment = e => setComment(e.target.value)
    const onChangeRating = e => setRating(e)

    const onPressReview = async () => {
        if (!rating) return notification.warning({ description: 'Please rate the product' })

        try {
            setIsLoading(true)
            await api.post(`/review`, {
                user_id: user.id,
                product_id: product.id,
                vendor_id: null,
                rating: rating,
                comment: comment,
            })
            onFinish()
        } catch (error) {
            if (error.response.status === 403)
                return notification.warning({ description: 'You have to buy the product to make a comment' })

            notification.error({ description: 'There was an error with your request.' })
        } finally {
            setIsLoading(false)
        }
    }
    return (
        <div className="post-review-product">
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
                        <Link to={`/product/${product.id}`}>{product.title ?? 'title'}</Link>
                    </div>
                    <div className="post-review-short-description">{product.short_description}</div>
                    <div className="post-review-rate">
                        <Rate onChange={onChangeRating} />
                        <div className="post-review-rate-comment">{'(Please rate the product)'}</div>
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
                <Button loading={isLoading} type="primary" onClick={onPressReview}>
                    Send Review
                </Button>
            </div>
        </div>
    )
}
