import { Checkbox } from 'antd'
import './CheckoutTermsAndConditions.less'
import { DistantSalesAgreement } from './DistantSalesAgreement'

export const CheckoutTermsAndConditions = ({onChange = () => {}}) => {

    const onAgreementChange = newValue => onChange(newValue.target.checked)

    return <div className="conditions-container">
        <div className="conditions-header">
            Terms and Conditions
        </div>
        <div className="conditions-agreement">
            <DistantSalesAgreement />
        </div>
        <div className="conditions-checkbox">
            <Checkbox onChange={onAgreementChange}>
                I agree to the terms listed in <strong>Distant Sales Agreement Form</strong>
            </Checkbox>
        </div>

    </div>
}