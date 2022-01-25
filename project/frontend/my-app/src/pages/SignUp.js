import React, { Component } from 'react'
import { Redirect } from 'react-router-dom'
// import css
import '../styles/SignUp.css'

// import axios
import axios from 'axios'

const api = axios.create({
  baseURL: 'http://34.72.123.63/api/customers?apikey=Zkfokey2311',
})

class SignUp extends Component {
  constructor() {
    super()
    // this.getCustomers()
    this.state = {
      firstname: '',
      lastname: '',
      username: '',
      email: '',
      password: '',
    }

    this.handleChange = this.handleChange.bind(this)
    this.handleSubmit = this.handleSubmit.bind(this)
  }

  // sets the state when inputs in sign up form are filled
  handleChange = (e) => {
    this.setState({ [e.target.name]: e.target.value })
  }

  // handles POST request when form is submitted
  handleSubmit = (e) => {
    e.preventDefault()
    console.log(this.state)
    axios
      .post('http://localhost:8090/customers/register', this.state)
      .then((response) => {
        console.log(response)
        const {temp} = this.state
        this.setState({
          ...temp,
          status: response.data
        })
        console.log(this.state)
      })
      .catch((error) => {
        console.log(error)
      })
  }

  // // holder for customer object in RestAPI
  // state = {
  //   customers: [],
  // }

  // // get all customers
  // getCustomers = async () => {
  //   let data = await api.get('/').then(({ data }) => data)
  //   console.log(data)
  //   this.setState({ customers: data })
  // }

  // // create a customer and save to RestAPI
  // createCustomer = async () => {
  //   let res = await api.post('/register', {
  //     id: 123,
  //     firstname: 'John',
  //     lastname: 'Doe',
  //     username: 'JohnDoe101',
  //     email: 'john@doe.com',
  //     password: 'password',
  //     loggedIn: false,
  //   })
  //   console.log(res)
  //   this.getCustomers()
  // }

  render() {
    const { firstname, lastname, username, email, password } = this.state

    // redirect if registration successful 
    if(this.state.status === 'Registration Successful')
    {
        return <Redirect to='/customerlogin' />
    }

    return (
      /*main content*/
      <div className='wrapper'>
        <div class='signUpContent'>
          <h2>Sign up!</h2>

          {/*Customers*/}
          {/* <button onClick={this.createCustomer}>createCustomer</button>
          {this.state.customers.map((customer) => (
            <h2 key={customer.id}>{customer.firstname}</h2>
          ))} */}
          <form onSubmit={this.handleSubmit}>
            <label for='firstname'>First name:</label>
            <br />
            <input
              onChange={this.handleChange}
              id='firstname'
              type='text'
              name='firstname'
              placeholder='John'
              value={firstname}
              size='70'
            />
            <br />
            <br />

            <label for='lastname'>Last name:</label>
            <br />
            <input
              onChange={this.handleChange}
              id='lastname'
              type='text'
              name='lastname'
              placeholder='Doe'
              value={lastname}
              size='70'
            />
            <br />
            <br />

            {/* <label for='username'>Username:</label>
            <br />
            <input
              onChange={this.handleChange} 
              id='username'
              type='text'
              name='username'
              placeholder='JohnDoe101'
              value={username}
              size='70'
            />
            <br />
            <br /> */}

            <label for='email'>E-mail:</label>
            <br />
            <input
              onChange={this.handleChange}
              type='email'
              id='email'
              name='email'
              placeholder='john@doe.com'
              value={email}
              size='70'
            />
            <br />
            <br />

            <label for='email'>Password:</label>
            <br />
            <input
              onChange={this.handleChange}
              type='password'
              id='password'
              name='password'
              placeholder='password'
              value={password}
              size='70'
            />
            <br />
            <br />
            <br />

            <button class='submit' type='submit'>
              Submit
            </button>
          </form>
        </div>
      </div>
    )
  }
}

export default SignUp
