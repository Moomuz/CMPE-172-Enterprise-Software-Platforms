import React, { Component, Fragment, useEffect } from 'react'
import { Redirect } from 'react-router-dom'
import axios from 'axios'
import '../../styles/EmployeeDashboard.css'

const api = axios.create({
  baseURL: 'http://localhost:8090/customers'
})

class AllOrders extends Component {
  constructor() {
    super()

    this.state = {
      orders: [],
      payments: [],
      ordernumber: '',
      firstname: '',
      lastname: '',
      drink: '',
      drinkSize: '',
      milk: ''
    }

  }

  componentDidMount() {
    axios.get('http://localhost:8080/orders')
      .then(response => {
        console.log(response)
          this.setState({
            orders: response.data
          })
        console.log(this.state)
      })
      .catch(error => {
        console.log(error)
      })
  }

  render() {
    
    return (
      <div>
          <div class='sectionTitle'>
            <p>All Orders</p>
          </div>

          <table>
            <tr>
              <th>Order ID</th>
              <th>Drink</th>
              <th>Drink Size</th>
              <th>Milk/No Milk</th>
            </tr>

            {this.state.orders.map(order =>
              <tr>
                <td>{order.id}</td>
                <td>{order.drink}</td>
                <td>{order.drinkSize}</td>
                <td>{order.milk}</td>
              </tr>
            )}
          </table>
        </div>
    )
  }
}

export default AllOrders
