# Solution to HackerRank problem : Insertion Sort Advanced Analysis
class InsertionSortSwaps
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
      total_elements = @all_inputs[input_idx].to_i
      input_idx += 1
      all_elements = @all_inputs[input_idx]
      input_idx += 1

      swaps = sort_and_count(all_elements.split(" ").map { |i| i.to_i })[1]
      puts swaps
    end
  end

  def merge(left_arr, right_arr)
    merged_array = []
    i, j, inversions = 0, 0, 0
   
    for k in 0...(left_arr.size + right_arr.size) do
       if ((left_arr[i] != nil and right_arr[j] != nil and left_arr[i] <= right_arr[j]) or (left_arr[i] != nil and right_arr[j] == nil))
          merged_array << left_arr[i]
          i += 1
       else
          merged_array << right_arr[j]
          j += 1
          inversions += left_arr.size - i
       end
    end
   
    return merged_array, inversions
  end
   
  def sort_and_count(arr)
    return arr, 0 if (arr.size == 1)
    
    mid = arr.size / 2;
     
    left_arr  = arr[0...mid]
    right_arr = arr[(mid)...arr.size]
   
    left_sorted_part, left_inversions    = sort_and_count(left_arr)
    right_sorted_part, right_inversions  = sort_and_count(right_arr)
    merged_array, merge_inversions       = merge(left_sorted_part, right_sorted_part)
   
    return merged_array, (left_inversions + right_inversions + merge_inversions)
  end  

end

is = InsertionSortSwaps.new
is.read_inputs
is.solve_problem
