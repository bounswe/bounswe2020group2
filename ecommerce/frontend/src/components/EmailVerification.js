import React, { useEffect, useState } from 'react'
import { api } from '../api'
import { notification } from 'antd'
import { sleep } from '../utils'
import './EmailVerification.less'
import { CheckCircleTwoTone, CloseCircleTwoTone } from '@ant-design/icons'

const VerificationMessage = verificationState => {
    console.log(verificationState)
    if (verificationState == 'Success') {
        return (
            <div className="verify-message">
                <p>Your account has been verified.</p>
                <CheckCircleTwoTone />
            </div>
        )
    } else if (verificationState == 'Invalid') {
        return (
            <div className="verify-message">
                <p>Failed to verify account</p>
                <CloseCircleTwoTone />
            </div>
        )
    } else if (verificationState == 'Expired') {
        return (
            <div className="verify-message">
                <p>Your activitation link is expired.</p>
            </div>
        )
    } else if (verificationState == 'Verified') {
        return (
            <div className="verify-message">
                <p>Your account is already been verified. </p>
            </div>
        )
    } else {
        return (
            <div className="verify-message">
                <p>Your account is being verified. Please wait...</p>
            </div>
        )
    }
}

export const EmailVerification = props => {
    const { id } = props.match.params
    const [verificationState, setVerificationState] = useState('waiting')

    useEffect(() => {
        async function fetch() {
            try {
                console.log('token: ', id)
                const { data } = await api.get(`/email-verify/${id}`)
                console.log(data)
                await sleep(2000)
                setVerificationState(data.message)
            } catch (error) {
                console.error('failed to verify account', error)
                setVerificationState('failure')
            } finally {
            }
        }
        fetch()
    }, [])

    return <div className="verify-message-box">{VerificationMessage(verificationState)}</div>
}
