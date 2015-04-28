# Solution to HackerRank problem : Stock Maximize
class StockMaximize
  def read_inputs
  	@total_inputs = gets.to_i
  	@all_inputs = Array.new(@total_inputs * 2)

    input_idx = 0
    while input_idx < @total_inputs * 2
      @all_inputs[input_idx] = gets.chomp
      input_idx += 1
    end
  end

  def solve_problem
    input_idx = 0
    while input_idx < @total_inputs * 2
      total_trading_days = @all_inputs[input_idx].to_i
      input_idx += 1
      trading_prices = @all_inputs[input_idx]
      input_idx += 1

      profit = calculate_profit(trading_prices, total_trading_days)
      puts profit
    end
  end

  def calculate_profit(trading_prices, total_trading_days)
    total_profit = 0

    trading_prices = trading_prices.split(" ").map { |i| i.to_i }
    max_trading_prices = Array.new(trading_prices)

    trading_idx = total_trading_days - 1
    while trading_idx > 0
      if max_trading_prices[trading_idx] > max_trading_prices[trading_idx - 1]
        max_trading_prices[trading_idx - 1] = max_trading_prices[trading_idx]
      end

      trading_idx -= 1
    end

    trading_idx = 0
    while trading_idx < total_trading_days - 1
      total_profit += max_trading_prices[trading_idx] - trading_prices[trading_idx]

      trading_idx += 1
    end

    return total_profit
  end
end

sm = StockMaximize.new
sm.read_inputs
sm.solve_problem
