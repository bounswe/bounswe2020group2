import React, { useEffect, useState } from 'react'
import { Pagination, Spin } from 'antd'
import './VendorReviews.less'
import { api } from '../../api'
import { VendorReview } from './VendorReview'

export const VendorReviews = ({ vendorId }) => {
    const [isLoading, setIsLoading] = useState(true)
    const [currentPage, setCurrentPage] = useState(1)
    const [reviews, setReviews] = useState(null)

    useEffect(() => {
        // When current page is changed, makes a call to backend and gets the new reviews.
        async function fetch() {
            try {
                setIsLoading(true)
                const {
                    data: { reviews },
                } = await api.get(`/review?vendor=${vendorId}`) // &page_size=${pageSize}&page=${currentPage - 1}
                setReviews(reviews)
            } catch (error) {
                console.error('Failed to load user reviews', error)
            } finally {
                setIsLoading(false)
            }
        }
        fetch()
    }, [currentPage])

    if (!isLoading && reviews === null) return null

    return (
        <div className="vendor-review">
            <Spin spinning={isLoading}>
                <h1>Vendor Reviews</h1>
                <div className="vendor-review-items">
                    {(reviews ?? []).map((review, index) => {
                        return (
                            <div key={review.id}>
                                <VendorReview review={review} />
                                {index < reviews.length - 1 && <hr style={{ margin: '5px' }}></hr>}
                            </div>
                        )
                    })}
                </div>
            </Spin>
        </div>
    )
}
