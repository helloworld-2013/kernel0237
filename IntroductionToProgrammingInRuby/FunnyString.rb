# Solution to HackerRank problem : Funny String
class FunnyString
  def read_inputs
    @total_inputs = gets.to_i
    @all_inputs = Array.new(@total_inputs)

    input_idx = 0
    while input_idx < @total_inputs
      @all_inputs[input_idx] = gets.chomp
      input_idx += 1
    end
  end

  def solve_problem
  	input_idx = 0
    while input_idx < @total_inputs
      if funny?(@all_inputs[input_idx])
      	puts "Funny"
      else
      	puts "Not Funny"
      end
      input_idx += 1
  	end
  end

  def funny?(something)
    total = something.length
    start_idx = 0
    end_idx = total - 1

    while start_idx < total - 1
      if not same_difference?(start_idx, end_idx, something)
        return false
      end

      start_idx += 1
      end_idx -= 1
    end

  	return true
  end

  def same_difference?(start_idx, end_idx, something)
    (something[start_idx+1].ord - something[start_idx].ord).abs == (something[end_idx-1].ord - something[end_idx].ord).abs
  end
end

fs = FunnyString.new
fs.read_inputs
fs.solve_problem
