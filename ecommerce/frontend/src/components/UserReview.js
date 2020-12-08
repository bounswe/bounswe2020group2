import React, { useEffect, useState } from 'react'
import { Rate, Pagination, Spin } from 'antd'
import { Link, useHistory } from 'react-router-dom'
import './UserReview.less'
import { sleep } from '../utils'
import { api } from '../api'

export const UserReview = (product_id, { initialPage = 1, defaultPageSize = 10 }) => {
    const [isLoading, setIsLoading] = useState(false)
    const history = useHistory()

    const [reviews, setReviews] = useState([
        {
            comment:
                'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor.',
            author: 'Jeff Einstein',
            date: 'December 23',
            rating: 4,
        },
        {
            comment: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor.',
            author: 'Hasan Kaya',
            date: 'December 11',
            rating: 3,
        },
    ])

    const values = {
        pagination: {
            pageSize: defaultPageSize,
            current: 1,
            pageSize: 1,
        },
    }

    const refreshReviewsWith = async (current, pageSize) => {
        setIsLoading(true)
        await sleep(2000)
        setIsLoading(false)
    }

    const onPaginationChanged = (current, pageSize) => {
        console.log('Current: ', current, 'Pagesize: ', pageSize)
        refreshReviewsWith(current, pageSize)
    }

    const Review = ({ review: { comment, author, date, rating } }) => {
        return (
            <div className="review-item-content">
                <div className="rate-and-author">
                    <div className="rate">
                        <Rate defaultValue={rating} disabled={true}></Rate>
                    </div>
                    <Link to="#">
                        <div className="author">
                            <p>{author}</p>
                        </div>
                    </Link>
                </div>
                <div className="comment-and-date">
                    <p>{comment}</p>
                    <p>{date}</p>
                </div>
            </div>
        )
    }

    return (
        <div>
            <Spin spinning={isLoading}>
                <div className="review-items">
                    <h1>User Reviews</h1>
                    {reviews.map((review, index) => {
                        return (
                            <div key={review.id}>
                                <Review review={review} />
                                {index < reviews.length - 1 && <hr></hr>}
                            </div>
                        )
                    })}
                </div>
            </Spin>

            <Pagination
                className="review-results-pagination"
                onChange={onPaginationChanged}
                defaultCurrent={1}
                total={50}
            />
        </div>
    )
}
