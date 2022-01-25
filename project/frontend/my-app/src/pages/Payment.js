import React, { Component } from 'react'
import { Redirect } from 'react-router-dom'
import axios from 'axios'
import '../styles/Payment.css'

// const api = axios.create({
//     baseURL: 'http://' // ???
// })

class Payment extends Component {
  constructor() {
    super()
    // this.getPayments()
    this.state = {
      firstname: '',
      lastname: '',
      address: '',
      city: '',
      state: '',
      zip: '',
      phonenumber: '',
      cardnumber: '',
      expmonth: '',
      expyear: '',
      cvv: '',
      email: '',
      notes: '',
      order: [],
      ordernumber: '',
      transactionamount: '',
      drink: '',
      drinkSize: '',
      milk: ''
    }

    this.handleChange = this.handleChange.bind(this)
    this.handleSubmit = this.handleSubmit.bind(this)
  }

  componentDidMount() {
    axios
      .get('http://localhost:8080/orders', this.state)
      .then((response) => {
        console.log(response)
        response.data.forEach((order) => {
          if (order.id) {
            this.setState({
              order: order,
              ordernumber: order.orderNumber,
              transactionamount: order.total,
              drink: order.drink,
              drinkSize: order.drinkSize,
              milk: order.milk
            })
          }
          console.log(this.state)
        })
      })
      .catch((error) => {
        console.log(error)
      })
  }

  // sets the state when inputs in sign up form are filled
  handleChange = (e) => {
    this.setState({ [e.target.name]: e.target.value })
  }

  // handle HTTP POST request when form is submitted
  handleSubmit = (e) => {
    e.preventDefault()
    console.log(this.state)
    axios
    .post('http://localhost:8070/payment/processpayment', this.state)
    .then((response) => {
        console.log(response)
        this.setState({
        status: response.data
        })
        console.log(this.state)
    })
    .catch((error) => {
        console.log(error)
    })

    console.log('Order Number: ' + this.state.ordernumber)
    axios
    .post('http://localhost:8080/order/setpaid', this.state.ordernumber)
    .then((response) => {
        console.log(response)
        this.setState({
            orderStatus: response.data
        })
        console.log(this.state)
        })
    .catch((error) => {
        console.log(error)
    })
  }

  render() {
    const {
      firstname,
      lastname,
      address,
      city,
      state,
      zip,
      phonenumber,
      cardnumber,
      expmonth,
      expyear,
      cvv,
      email,
      notes,
    } = this.state

    // redirect back to customer dashboard once payment goes through
    if (
      this.state.status ===
      'Thank you for your payment! Your order number is: ' +
        this.state.ordernumber
    ) {
      return <Redirect to='/customerdashboard' />
    }

    // display the payment form
    return (
      <div class='wrapper'>
        <div class='paymentContent'>
            <div class='paymentSectionTitle'>
                <p>Order Details</p>
            </div>

            <div>
                <table class='orderTable'>
                    {/* <tr class='orderRow'>
                        <td class='orderKey'><b>Order Number: </b></td>
                        <td class='orderData'>{this.state.ordernumber}</td>
                    </tr> */}
                    <tr class='orderRow'>
                        <td class='orderKey'><b>Total Price: </b></td>
                        <td class='orderData'>{this.state.transactionamount}</td>
                    </tr>
                    <tr class='orderRow'>
                        <td class='orderKey'><b>Drink: </b></td>
                        <td class='orderData'>{this.state.drink}</td>
                    </tr>
                    <tr class='orderRow'>
                        <td class='orderKey'><b>Drink Size: </b></td>
                        <td class='orderData'>{this.state.drinkSize}</td>
                    </tr>
                    <tr class='orderRow'>
                        <td class='orderKey'><b>Milk/No Milk: </b></td>
                        <td class='orderData'>{this.state.milk}</td>
                    </tr>
                </table>
            </div>
            
            <div class='paymentSectionTitle'>
                <p>Payment Information</p>
            </div>

            <form onSubmit={this.handleSubmit}>
            <div className='payment-form'>
                <div>
                <label for='firstname'>First name: </label>
                <br />
                <input
                    onChange={this.handleChange}
                    id='firstname'
                    type='text'
                    name='firstname'
                    placeholder='John'
                    size='50'
                />
                <br />
                <br />

                <label for='lastname'>Last name: </label>
                <br />
                <input
                    onChange={this.handleChange}
                    id='lastname'
                    type='text'
                    name='lastname'
                    placeholder='Doe'
                    size='50'
                />
                <br />
                <br />

                <label for='address'>Address: </label>
                <br />
                <input
                    onChange={this.handleChange}
                    id='address'
                    type='text'
                    name='address'
                    placeholder='542 W. 15th Street'
                    size='50'
                />
                <br />
                <br />

                <label for='city'>City: </label>
                <br />
                <input
                    onChange={this.handleChange}
                    id='city'
                    type='text'
                    name='city'
                    placeholder='New York'
                    size='50'
                />
                <br />
                <br />

                <label for='state'>State: </label>
                <br />
                <input
                    onChange={this.handleChange}
                    id='state'
                    type='text'
                    name='state'
                    placeholder='NY'
                    size='50'
                />
                <br />
                <br />

                <label for='zip'>Zip code: </label>
                <br />
                <input
                    onChange={this.handleChange}
                    id='zip'
                    type='text'
                    name='zip'
                    placeholder='10001'
                    size='50'
                />
                <br />
                <br />

                <label for='phonenumber'>Phone number: </label>
                <br />
                <input
                    onChange={this.handleChange}
                    id='phonenumber'
                    type='text'
                    name='phonenumber'
                    placeholder='(408) 123-0456'
                    size='50'
                />
                <br />
                <br />
                </div>

                <div class='rightSide'>
                {/* valid card information used as placeholders here */}
                <label for='cardnumber'>Card number: </label>
                <br />
                <input
                    onChange={this.handleChange}
                    id='cardnumber'
                    type='text'
                    name='cardnumber'
                    placeholder='4622-9431-2501-3713'
                    size='50'
                />
                <br />
                <br />

                <label for='expmonth'>Expiration month: </label>
                <br />
                <input
                    onChange={this.handleChange}
                    id='expmonth'
                    type='text'
                    name='expmonth'
                    placeholder='December'
                    size='50'
                />
                <br />
                <br />

                <label for='expyear'>Expiration year: </label>
                <br />
                <input
                    onChange={this.handleChange}
                    id='expyear'
                    type='text'
                    name='expyear'
                    placeholder='2022'
                    size='50'
                />
                <br />
                <br />

                <label for='cvv'>CVV: </label>
                <br />
                <input
                    onChange={this.handleChange}
                    id='cvv'
                    type='text'
                    name='cvv'
                    placeholder='043'
                    size='50'
                />
                <br />
                <br />

                <label for='email'>Email: </label>
                <br />
                <input
                    onChange={this.handleChange}
                    id='email'
                    type='email'
                    name='email'
                    placeholder='john@example.com'
                    size='50'
                />
                <br />
                <br />

                <label for='notes'>Notes: </label>
                <br />
                <input
                    onChange={this.handleChange}
                    id='notes'
                    type='text'
                    name='notes'
                    placeholder='special instructions'
                    size='50'
                />
                <br />
                <br />
                </div>
            </div>

            <button class='paymentSubmit' type='submit'>
                Submit
            </button>
            </form>
        </div>
    </div>
    )
  }
}

export default Payment
