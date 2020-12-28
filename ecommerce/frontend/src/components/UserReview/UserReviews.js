import React, { useEffect, useState } from 'react'
import { Pagination, Spin } from 'antd'
import './UserReviews.less'
import { api } from '../../api'
import { UserReview } from './UserReview'

export const UserReviews = ({ productId }) => {
    const [isLoading, setIsLoading] = useState(true)
    const [currentPage, setCurrentPage] = useState(1)
    const [reviews, setReviews] = useState(null)
    const [totalPage, setTotalPage] = useState(0)
    const [pageSize, setPageSize] = useState(10)

    useEffect(() => {
        // When current page is changed, makes a call to backend and gets the new reviews.
        async function fetch() {
            try {
                setIsLoading(true)
                const {
                    data: {
                        reviews,
                        // pagination: { page_size, page, total_items },
                    },
                } = await api.get(`/review?product=${productId}`) // &page_size=${pageSize}&page=${currentPage - 1}
                setReviews(reviews)
            } catch (error) {
                console.error('Failed to load user reviews', error)
            } finally {
                setIsLoading(false)
            }
        }
        fetch()
    }, [currentPage])

    console.log('reviews', reviews)
    if (!isLoading && reviews === null) return null

    const onPaginationChanged = value => {
        // When page count is changed, current page is set
        setCurrentPage(value)
    }

    return (
        <div className="user-review">
            <Spin spinning={isLoading}>
                <h1>User Reviews</h1>
                <div className="user-review-items">
                    {(reviews ?? []).map((review, index) => {
                        return (
                            <div key={review.id}>
                                <UserReview review={review} />
                                {index < reviews.length - 1 && <hr style={{ margin: '5px' }}></hr>}
                            </div>
                        )
                    })}
                </div>
            </Spin>

            <Pagination
                className="review-results-pagination"
                onChange={onPaginationChanged}
                current={currentPage}
                defaultPageSize={pageSize}
                total={totalPage}
            />
        </div>
    )
}
